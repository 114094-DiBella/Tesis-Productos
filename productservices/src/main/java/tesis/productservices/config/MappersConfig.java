package tesis.productservices.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tesis.productservices.entities.*;
import tesis.productservices.models.Products;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ModelMapper and ObjectMapper configuration class.
 */
@Configuration
public class MappersConfig {

    /**
     * The ModelMapper bean by default.
     *
     * @return the ModelMapper by default.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * The ModelMapper bean to merge objects.
     *
     * @return the ModelMapper to use in updates.
     */
    @Bean("mergerMapper")
    public ModelMapper mergerMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    /**
     * The ObjectMapper bean.
     *
     * @return the ObjectMapper with JavaTimeModule included.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * The ModelMapper bean to merge strict objects.
     * @return the ModelMapper to use in updates.
     */
    @Bean("strictMapper")
    public ModelMapper strictMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    // Agrega esto a tu archivo MappersConfig.java en el método modelMapper()

    /*@Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Agregar mapeo personalizado de ProductsEntity a Products
        mapper.createTypeMap(ProductsEntity.class, Products.class)
                .addMappings(mapping -> {
                    // Omitir el mapeo directo del campo images
                    mapping.skip(Products::setImageUrls);
                })
                .setPostConverter(context -> {
                    ProductsEntity source = context.getSource();
                    Products destination = context.getDestination();

                    // Conversión manual de lista de ProductImageEntity a lista de strings de URL
                    if (source.getImages() != null && !source.getImages().isEmpty()) {
                        List<String> imageUrls = source.getImages().stream()
                                .map(ProductImageEntity::getImageUrl)
                                .collect(Collectors.toList());
                        destination.setImageUrls(imageUrls);
                    }

                    return destination;
                });

        return mapper;
    }*/



}