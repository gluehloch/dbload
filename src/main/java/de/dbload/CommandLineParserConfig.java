/*
 * Copyright 2022 Andre Winkler
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

package de.dbload;

import org.apache.commons.cli.Options;

public class CommandLineParserConfig {

    private CommandLineParserConfig() {
    }
    
    public static Options create() {
        Options options = new Options();
        options.addOption("t", "tables", false, "File with table names to export");
        
        return options;
    }
    
}
