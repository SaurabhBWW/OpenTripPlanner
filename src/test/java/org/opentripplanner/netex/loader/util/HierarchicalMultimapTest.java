package org.opentripplanner.netex.loader.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opentripplanner.netex.loader.util.E.EASTWOOD;
import static org.opentripplanner.netex.loader.util.E.REAGAN;
import static org.opentripplanner.netex.loader.util.E.SCHWARZENEGGER;
import static org.opentripplanner.netex.loader.util.SetSupport.listOf;
import static org.opentripplanner.netex.loader.util.SetSupport.setOf;
import static org.opentripplanner.netex.loader.util.SetSupport.sort;

/**
 * We uses a simplified version of the data structure used in the {@link HierarchicalMapTest}:
 * <pre>
 * Level\Role | President | Governor         | Actor
 * -----------|-----------|------------------|---------------
 *    Country | [Reagan]  |                  | Eastwood
 *      State |           | [Schwarzenegger] | Schwarzenegger
 * </pre>
 * Now we use this test setup to test the {@link HierarchicalMultimap}.
 */
public class HierarchicalMultimapTest {

    private static final String PRESIDENT = "president";
    private static final String GOVERNOR = "governor";
    private static final String ACTOR = "actor";

    private final HierarchicalMultimap<String, E> country = new HierarchicalMultimap<>();
    private final HierarchicalMultimap<String, E> state = new HierarchicalMultimap<>(country);

    private static final List<E> PRESIDENTS = Collections.singletonList(REAGAN);
    private static final List<E> ACTORS_COUNTRY = Collections.singletonList(EASTWOOD);
    private static final List<E> ACTORS_STATE = Collections.singletonList(SCHWARZENEGGER);


    @Before
    public void setup() {
        country.add(PRESIDENT, REAGAN);
        state.add(GOVERNOR, SCHWARZENEGGER);

        country.add(ACTOR, EASTWOOD);
        state.add(ACTOR, SCHWARZENEGGER);
    }

    @Test public void testAddAllAndLocalMethods() {
        // Given
        HierarchicalMultimap<String, E> subject = new HierarchicalMultimap<>();
        Multimap<String, E> input = ArrayListMultimap.create();

        input.put("A", EASTWOOD);
        input.put("A", SCHWARZENEGGER);
        input.put("B", REAGAN);

        // When
        subject.addAll(input);

        // Then
        assertEquals(setOf("A", "B"),  subject.localKeys());
        assertEquals(sort(listOf(EASTWOOD, SCHWARZENEGGER)), sort(subject.localGet("A")));
        assertEquals(sort(singleton(REAGAN)),  sort(subject.localGet("B")));
        assertTrue(subject.localContainsKey("A"));
    }

    @Test public void addAndLookup() {
        // Given added elements in the setup method

        // Then expect Reagen to be president at all levels
        assertEquals(PRESIDENTS, country.lookup(PRESIDENT));
        assertEquals(PRESIDENTS, state.lookup(PRESIDENT));

        // And then the right actor for each level
        assertEquals(ACTORS_COUNTRY, country.lookup(ACTOR));
        assertEquals(ACTORS_STATE, state.lookup(ACTOR));

        // And no governor at country level; Hence an empty collection is returned
        assertTrue(country.lookup(GOVERNOR).isEmpty());
    }

    @Test public void localKeys() {
        assertEquals(setOf(PRESIDENT, ACTOR), country.localKeys());
        assertEquals(setOf(GOVERNOR, ACTOR), state.localKeys());
    }
}