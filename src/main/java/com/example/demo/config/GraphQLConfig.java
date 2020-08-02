package com.example.demo.config;

import com.example.demo.service.BookService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class GraphQLConfig {

    @Autowired
    private BookService bookService;


    @Bean
    public GraphQL graphQL() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        ClassPathResource classPathResource = new ClassPathResource("schema.graphql");
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(classPathResource.getInputStream());
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBook",bookService.getBook()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBooks", bookService.getBooks()))
                .type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("createBook", bookService.createBook()))
                .build();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry,runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
