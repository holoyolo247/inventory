package com.readJSON.demo;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.readJSON.demo.models.Expense;
import com.readJSON.demo.models.Product;
import com.readJSON.demo.models.User;
import com.readJSON.demo.repository.ExpenseRepository;
import com.readJSON.demo.repository.ProductRepository;
import com.readJSON.demo.repository.UserRepository;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class dataLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private ObjectMapper objectMapper;
    private ProductRepository productRepository;
    private ExpenseRepository expenseRepository;
    @Autowired
    public dataLoader(UserRepository userRepository, ExpenseRepository expenseRepository,ProductRepository productRepository,ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.productRepository =productRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // read data and insert into database

        List<String> targets = new ArrayList<>();
        targets.add("users");
        targets.add("products");
        targets.add("expenses");
//        targets.add("expenseSummary");
//        targets.add("salesSummary");
//        targets.add("purchaseSummary");

//        targets.add("sales");
//        targets.add("purchaseSummary");
//        targets.add("purchases");
//
//
//        targets.add("expenseByCategory");



        List<User> users = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        for(String s: targets){
            JsonNode json;
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/"+s+".json")) {

                json = objectMapper.readValue(inputStream, JsonNode.class);



                if(s.equals("users")){
                    for(JsonNode j: json){
                        users.add(mapToEntityUser(j));
                    }
                    userRepository.saveAll(users);
                }

                if(s.equals("products")){
                    for (JsonNode j: json){
                        products.add(mapToEntityProduct(j));
                    }
                    productRepository.saveAll(products);
                }


                if(s.equals("expenses")){
                    for(JsonNode j: json){
                        expenses.add(mapToEntityExpense(j));
                    }
                    expenseRepository.saveAll(expenses);

                }



            }catch (IOException e){
                throw  new RuntimeException("Failed to read JSON data");
            }
        }
    }

    private User mapToEntityUser(JsonNode json){
        User user = new User();
        user.setUserId(json.get("userId").asText());
        user.setEmail(json.get("email").asText());
        user.setName(json.get("name").asText());
        return user;
    }

    private Product mapToEntityProduct(JsonNode json){
        Product product = new Product();
        product.setProductId(json.get("productId").asText());
        product.setName(json.get("name").asText());
        product.setPrice(json.get("price").asDouble());
        product.setRating(json.get("rating").asDouble());
        product.setStockQuantity(json.get("stockQuantity").asLong());
        return product;
    }

    private Expense mapToEntityExpense(JsonNode json){
        Expense expense = new Expense();
        expense.setExpenseId(json.get("expenseId").asText());
        expense.setCategory(json.get("category").asText());
        expense.setAmount(json.get("amount").asDouble());
        OffsetDateTime  timestamp = extractTimeStamp(json);
        expense.setTimestamp(timestamp);
        return  expense;

    }

    private OffsetDateTime  extractTimeStamp(JsonNode json) {

        String timeStampString = json.get("timestamp").asText();
        return OffsetDateTime .parse(timeStampString);
    }
}
