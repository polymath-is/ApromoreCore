/*-
 * #%L
 * This file is part of "Apromore Core".
 * 
 * Copyright (C) 2016 - 2017 Queensland University of Technology.
 * %%
 * Copyright (C) 2018 - 2020 The University of Melbourne.
 * %%
 * Copyright (C) 2020, Apromore Pty Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

package org.apromore.portal.util;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for {@link ExplicitComparator}.
 */
public class ExplicitComparatorUnitTest {

    @Test
    public void testCompare() throws Exception {
        ExplicitComparator c = new ExplicitComparator("Cherry,Apple,Banana");

        // Try explicitly ordered elements
        assertTrue(c.compare("Apple", "Banana") < 0);
        assertEquals(0, c.compare("Banana", "Banana"));
        assertTrue(c.compare("Banana", "Apple") > 0);

        // Try an element that doesn't occur within the list
        assertTrue(c.compare("Aardvark", "Apple") < 0);
        assertEquals(0, c.compare("Aardvark", "Aardvark"));
        assertTrue(c.compare("Apple", "Aardvark") > 0);

        // Sort a list
        String[] actual = {"Apple", "Banana", "Cherry", "Aardvark"};
        Arrays.sort(actual, c);
        String[] expected = {"Aardvark", "Cherry", "Apple", "Banana"};
        assertEquals(expected, actual);
    }
}
