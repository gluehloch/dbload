/*
 * Copyright 2014 Andre Winkler
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.dbload.csv;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.nio.charset.Charset;

import org.junit.Test;

/**
 * Check the environment.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class EnvironmentalTest {

    @Test
    public void testUTF8Charset() {
        Charset charset = Charset.forName("UTF-8");
        assertThat(charset, notNullValue());
    }

}
