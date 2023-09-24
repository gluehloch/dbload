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

package de.dbload.misc;

import java.time.Duration;
import java.time.Instant;

/**
 * A stop watch to measure some things.
 *
 * @author <a href="https://www.andre-winkler.de">Andre Winkler</a>
 */
public class StopWatch {

    private Instant startTime;
    private Instant endTime;

    public void start() {
        startTime = Instant.now();
    }

    public void stop() {
        endTime = Instant.now();
    }

    public Duration duration()  {
        return Duration.between(startTime, endTime);
    }

}
