package org.example.clone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CloneLib should ")
class CloneLibTest {

    private final CloneLib cloneLib = CloneLibInitializer.createCloneLib();

    @DisplayName("clone objects with simple properties")
    @Test
    public void shouldCloneObjectsWithSimpleProperties() {
        ClassWithSimpleProperties original = new ClassWithSimpleProperties("strValue", 5);
        ClassWithSimpleProperties clone = cloneLib.deepCopy(original);
        assertThat(clone)
                .isNotSameAs(original)
                .usingRecursiveComparison()
                .isEqualTo(original);
    }

    @DisplayName("clone objects with collections")
    @Test
    public void shouldCloneObjectsWithCollections() {
        List<Object> list = new ArrayList<>(List.of(
                new ClassWithSimpleProperties("1", 1)
        ));
        ClassWithCollection classWithCollection = new ClassWithCollection(list);
        list.add(classWithCollection); // let's add a ref to itself and check if it would work ;)

        ClassWithCollection clone = cloneLib.deepCopy(classWithCollection);
        assertThat(clone)
                .isNotSameAs(classWithCollection)
                .satisfies(result -> {
                    Collection<Object> collection = result.getCollection();
                    assertThat(collection).element(0).isNotSameAs(list.get(0)).usingRecursiveComparison().isEqualTo(list.get(0));
                    assertThat(collection).element(1).isSameAs(result).isNotSameAs(classWithCollection);
                });
    }

    @DisplayName("clone objects with maps")
    @Test
    public void shouldCloneObjectsWithMaps() {
        Map<String, Object> map = new HashMap<>(Map.of(
                "1", 1,
                "2", new ClassWithSimpleProperties("strValue", 5),
                "3", new ArrayList<>(List.of(new ClassWithSimpleProperties("strValue", 1)))
        ));
        ClassWithMap classWithMap = new ClassWithMap(map);
        ClassWithMap clone = cloneLib.deepCopy(classWithMap);
        assertThat(clone)
                .isNotSameAs(classWithMap)
                .satisfies(result -> {
                    assertThat(result.map.get("1")).isEqualTo(map.get("1"));
                    assertThat(result.map.get("2"))
                            .isNotSameAs(map.get("2"))
                            .usingRecursiveComparison()
                            .isEqualTo(map.get("2"));
                    assertThat(result.map.get("3"))
                            .isNotSameAs(map.get("3"))
                            .usingRecursiveComparison()
                            .isEqualTo(map.get("3"));
                });
    }

    @DisplayName("clone objects with arrays")
    @Test
    public void shouldCloneObjectsWithArrays() {
        Object[] array = new Object[] {
                new ClassWithSimpleProperties("strValue", 1),
                new Object[] {new ClassWithSimpleProperties("some string", 8)}
        };
        ClassWithArray classWithArray = new ClassWithArray(array);
        ClassWithArray clone = cloneLib.deepCopy(classWithArray);

        assertThat(clone)
                .isNotSameAs(classWithArray)
                .satisfies(result -> {
                    Object[] arr = result.getArray();
                    assertThat(arr).isNotSameAs(array).usingRecursiveComparison().isEqualTo(array);
                    assertThat(arr[1]).isNotSameAs(array[1]);
                });
    }

    @DisplayName("clone arrays")
    @Test
    public void shouldCloneArrays() {
        Object[] array = new Object[] {
                new ClassWithSimpleProperties("strValue", 1),
                new Object[] {new ClassWithSimpleProperties("some string", 8)}
        };
        Object[] clone = cloneLib.deepCopy(array);
        assertThat(clone)
                .isNotSameAs(array)
                .usingRecursiveComparison()
                .isEqualTo(array);
    }

    public static class ClassWithSimpleProperties {

        private final String strValue;

        private final int intValue;

        public ClassWithSimpleProperties(String strValue, int intValue) {
            this.strValue = strValue;
            this.intValue = intValue;
        }

    }

    public static class ClassWithCollection {

        private final Collection<Object> collection;

        public ClassWithCollection(Collection<Object> collection) {
            this.collection = collection;
        }

        public Collection<Object> getCollection() {
            return collection;
        }
    }

    public static class ClassWithMap {

        private final Map<String, Object> map;

        public ClassWithMap(Map<String, Object> map) {
            this.map = map;
        }
    }

    public static class ClassWithArray {

        private final Object[] array;

        public ClassWithArray(Object[] array) {
            this.array = array;
        }

        public Object[] getArray() {
            return array;
        }
    }

}