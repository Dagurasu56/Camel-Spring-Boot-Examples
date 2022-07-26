/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.jira;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.component.jira.JiraConstants.ISSUE_KEY;

@Component
public class AttachFileRoute extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(AttachFileRoute.class);

    @Value("${example.jira.issue-attach}")
    private String issue;

    @Override
    public void configure() {
        LOG.info(" >>>>>>>>>>>>>>>>>>>>> jira example - add attachment");
        // change the fields accordinly to your target jira server
        from("file://{{example.jira.upload-directory}}?fileName={{example.jira.upload-file-name}}&noop=true&delay=50000")
                .setHeader(ISSUE_KEY, () -> issue)
                .log("  JIRA attach: ${header.camelFileName} to ${headers.IssueKey}")
                .to("jira://attach");


    }

}
