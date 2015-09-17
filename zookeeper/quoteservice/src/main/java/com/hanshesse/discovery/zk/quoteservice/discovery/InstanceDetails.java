/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.hanshesse.discovery.zk.quoteservice.discovery;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Provides additional details for services
 *
 * Taken from https://github.com/apache/curator/blob/master/curator-examples/src/main/java/discovery/InstanceDetails.java
 */
@JsonRootName("details")
public class InstanceDetails {
    private String description;

    public InstanceDetails()
    {
        this("");
    }

    public InstanceDetails(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstanceDetails that = (InstanceDetails) o;

        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }
}
