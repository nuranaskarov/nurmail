/*
 * Copyright (c) 2026 Nuran Askarov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package az.nuran.nurmail;

import az.nuran.nurmail.exception.NurmailResourceLoadingFailedException;

import java.io.InputStream;

/**
 * Nurmail - a simple HTML email template engine
 */
public class Nurmail {

    /**
     * Creates template builder from an HTML string
     *
     * @param content HTML string template
     * @return {@link NurmailBuilder} template builder
     */
    public static NurmailBuilder fromString(String content) {
        return new NurmailBuilder(content);
    }


    /**
     * Creates template builder by resource name
     *
     * @param resourceName valid HTML resource name with extension
     * @return {@link NurmailBuilder} template builder
     */
    public static NurmailBuilder fromResource(String resourceName) {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
            assert stream != null;
            return fromString(new String(stream.readAllBytes()));
        } catch (Exception e) {
            throw new NurmailResourceLoadingFailedException(resourceName, e.getMessage());
        }
    }
}
