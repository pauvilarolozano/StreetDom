package com.streetdom.boot.test;

import com.streetdom.adapters.out.valhalla.ValhallaEdgeReader;
import com.streetdom.adapters.out.valhalla.model.ValhallaEdgeData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
class ValhallaEdgeReaderIT {

    @Autowired
    private ValhallaEdgeReader edgeReader;

    @Test
    void shouldInspectDuplicateEdgesAcrossNeighborTiles() {

        List<ValhallaEdgeData> firstTileEdges =
                edgeReader.readEdges(
                        14,
                        8290,
                        6119
                );

        List<ValhallaEdgeData> secondTileEdges =
                edgeReader.readEdges(
                        14,
                        8291,
                        6119
                );

        Map<Long, ValhallaEdgeData> firstTileById =
                firstTileEdges.stream()
                        .collect(Collectors.toMap(
                                ValhallaEdgeData::id,
                                edge -> edge
                        ));

        List<ValhallaEdgeData> duplicatedEdges =
                secondTileEdges.stream()
                        .filter(edge -> firstTileById.containsKey(edge.id()))
                        .toList();

        System.out.println(
                "Duplicated edges: " + duplicatedEdges.size()
        );

        for (ValhallaEdgeData duplicatedEdge : duplicatedEdges) {

            ValhallaEdgeData firstTileEdge =
                    firstTileById.get(duplicatedEdge.id());

            System.out.println(
                    "EDGE: " + duplicatedEdge.id()
            );

            System.out.println(
                    "First tile geometry:"
            );

            firstTileEdge.coordinates()
                    .forEach(System.out::println);

            System.out.println(
                    "Second tile geometry:"
            );

            duplicatedEdge.coordinates()
                    .forEach(System.out::println);

            System.out.println("-------------------");
        }
    }
}