package com._daysOfBatman.SpringBootPractice.Orders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Full context + real converters, so content negotiation (406/415) is genuinely exercised.
// Tests create their own orders and never assume global state, so they are order-independent.
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    private long createOrder(String json) throws Exception {
        MvcResult result = mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn();
        Matcher m = Pattern.compile("\"id\":(\\d+)").matcher(result.getResponse().getContentAsString());
        if (!m.find()) throw new AssertionError("no id in response");
        return Long.parseLong(m.group(1));
    }

    @Test
    void listReturnsJsonArray() throws Exception {
        mvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));
    }

    @Test
    void listFiltersByStatusParam() throws Exception {
        mvc.perform(get("/orders").param("status", "SHIPPED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].status", everyItem(is("SHIPPED"))));
    }

    @Test
    void invalidStatusParamIs400() throws Exception {
        mvc.perform(get("/orders").param("status", "NOPE")).andExpect(status().isBadRequest());
    }

    @Test
    void createReturns201WithLocationAndIgnoresClientId() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":9999,\"item\":\"Cowl\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/orders/")))
                .andExpect(jsonPath("$.id", org.hamcrest.Matchers.not(9999)))
                .andExpect(jsonPath("$.item", is("Cowl")))
                .andExpect(jsonPath("$.status", is("CREATED")));
    }

    @Test
    void locationHeaderPointsAtTheCreatedResource() throws Exception {
        long id = createOrder("{\"item\":\"Gauntlets\"}");
        mvc.perform(get("/orders/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item", is("Gauntlets")));
    }

    @Test
    void getMissingIs404() throws Exception {
        mvc.perform(get("/orders/987654")).andExpect(status().isNotFound());
    }

    @Test
    void nonNumericIdIs400() throws Exception {
        mvc.perform(get("/orders/abc")).andExpect(status().isBadRequest());
    }

    @Test
    void putReplacesExistingOrder() throws Exception {
        long id = createOrder("{\"item\":\"Old\"}");
        mvc.perform(put("/orders/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"item\":\"New\",\"status\":\"SHIPPED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.item", is("New")))
                .andExpect(jsonPath("$.status", is("SHIPPED")));
    }

    @Test
    void putMissingIs404AndDoesNotCreate() throws Exception {
        mvc.perform(put("/orders/987655").contentType(MediaType.APPLICATION_JSON).content("{\"item\":\"x\"}"))
                .andExpect(status().isNotFound());
        mvc.perform(get("/orders/987655")).andExpect(status().isNotFound());
    }

    @Test
    void deleteIs204ThenSecondDeleteIs404() throws Exception {
        long id = createOrder("{\"item\":\"Temp\"}");
        mvc.perform(delete("/orders/" + id)).andExpect(status().isNoContent()).andExpect(content().string(""));
        mvc.perform(delete("/orders/" + id)).andExpect(status().isNotFound());
        mvc.perform(get("/orders/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void malformedJsonIs400() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content("{not json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidEnumInBodyIs400() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"item\":\"x\",\"status\":\"FLYING\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missingBodyIs400() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void wrongContentTypeIs415() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.TEXT_PLAIN).content("item=x"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void unacceptableAcceptHeaderIs406() throws Exception {
        mvc.perform(get("/orders").accept(MediaType.APPLICATION_XML)).andExpect(status().isNotAcceptable());
    }

    @Test
    void wrongMethodIs405() throws Exception {
        mvc.perform(delete("/orders")).andExpect(status().isMethodNotAllowed());
    }
}
