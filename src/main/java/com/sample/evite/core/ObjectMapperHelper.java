package com.sample.evite.core;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperHelper {

        private static ModelMapper modelMapper = new ModelMapper();

        static {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }

        /**
         * Copy entity object list to DTO object list
         *
         * @param entityList source list object
         * @param outCLass   destination class
         * @param <D>
         * @param <T>
         * @return
         */
        public static <D, T> List<D> mapList(final Collection<T> entityList, Class<D> outCLass) {
            return entityList.stream()
                    .map(entity -> map(entity, outCLass))
                    .collect(Collectors.toList());
        }

        /**
         * map entity object with DTO class
         *
         * @param entity   source object
         * @param outClass destination class
         * @param <D>
         * @param <T>
         * @return
         */
        public static <D, T> D map(final T entity, Class<D> outClass) {
            return modelMapper.map(entity, outClass);
        }

        /**
         * map entity object with DTO object
         *
         * @param source      source object
         * @param destination destination object
         * @param <S>
         * @param <D>
         * @return
         */
        public static <S, D> D map(final S source, D destination) {
            modelMapper.map(source, destination);
            return destination;
        }

    }

