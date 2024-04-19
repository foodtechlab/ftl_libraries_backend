package io.foodtechlab.common.core.entities;

import io.foodtechlab.common.core.entities.SortedData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SortedDataTest {
    @Test
    public void testCreate() {
        List<String> original = IntStream.range(0, 10).mapToObj(a -> UUID.randomUUID().toString()).collect(Collectors.toList());

        SortedData<String> sortedData = SortedData.of(original);

        assertEquals(sortedData.getValues().size(), original.size());
        assertEquals(sortedData.stream().count(), original.size());

        int num = -1;
        for (String data : sortedData) {
            num++;
            assertEquals(data, original.get(num));
        }
    }

    @Test
    public void testRemove() {
        List<String> original = IntStream.range(0, 10).mapToObj(a -> UUID.randomUUID().toString()).collect(Collectors.toList());

        SortedData<String> sortedData = SortedData.of(original);

        String toRemove = original.get(4);

        sortedData.delete(toRemove);

        assertEquals(sortedData.stream().count(), 9);
        assertFalse(sortedData.contains(toRemove));
        assertFalse(sortedData.stream().anyMatch(d -> d.equals(toRemove)));
    }

    @Test
    public void testRemoveIf() {
        List<String> original = IntStream.range(0, 10).mapToObj(a -> UUID.randomUUID().toString()).collect(Collectors.toList());

        SortedData<String> sortedData = SortedData.of(original);

        String toRemove = original.get(4);

        sortedData.removeIf(v -> v.equals(toRemove));

        assertEquals(sortedData.stream().count(), 9);
        assertFalse(sortedData.contains(toRemove));
        assertFalse(sortedData.stream().anyMatch(d -> d.equals(toRemove)));
    }
}
