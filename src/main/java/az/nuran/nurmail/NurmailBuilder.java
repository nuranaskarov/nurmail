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

import az.nuran.nurmail.exception.NurmailMissingSectionException;
import az.nuran.nurmail.exception.NurmailMissingVariableException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NurmailBuilder {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{\\s*([A-z0-9_]+)\\s*\\}\\}");
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String BLANK = "";

    private final String content;
    private final Map<String, String> context = new HashMap<>();

    /**
     * Initializes template builder with content
     * @param content HTML string representing an email template
     */
    public NurmailBuilder(String content) {
        this.content = content;
    }

    /**
     * Defines a context variable
     * @param varName name of the variable in the template
     * @param varValue value of the variable that will be replaced
     * @return {@link NurmailBuilder}
     */
    public NurmailBuilder with(String varName, String varValue) {
        this.context.put(varName, varValue);
        return this;
    }

    /**
     * Builds the final HTML email string
     * @return {@link NurmailTemplate} built HTML email template
     */
    public NurmailTemplate build() {
        Document document = Jsoup.parse(content);
        Element title = getElement(document, TITLE);
        Element body = getElement(document, BODY);
        return new NurmailTemplate(
                repalcePlaceholders(title.text()),
                repalcePlaceholders(body.html())
        );
    }

    private Element getElement(Document document, String sectionName) {
        Element element = document.selectFirst(sectionName);
        if (element == null) {
            throw new NurmailMissingSectionException(sectionName);
        }
        return element;
    }

    private String repalcePlaceholders(String content) {
        if (content == null || content.isEmpty()) {
            return BLANK;
        }
        return VARIABLE_PATTERN.matcher(content).replaceAll(match -> {
            String varName = match.group(1);
            String varValue = context.get(varName);
            if (varValue == null) {
                throw new NurmailMissingVariableException(varName);
            }
            return Matcher.quoteReplacement(varValue);
        });
    }
}
