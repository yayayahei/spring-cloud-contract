/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.contract.spec

import org.springframework.cloud.contract.spec.internal.DslProperty
import org.springframework.cloud.contract.spec.internal.QueryParameters
import org.springframework.cloud.contract.spec.internal.Url
import org.springframework.cloud.contract.spec.internal.UrlPath
import java.lang.IllegalStateException
import java.util.stream.Collectors

/**
 * @author Tim Ysewyn
 */
infix fun Url.withQueryParameters(parameters: QueryParameters.() -> Unit): Url {
    this.queryParameters = QueryParameters().apply(parameters)
    return this
}

infix fun UrlPath.withQueryParameters(parameters: QueryParameters.() -> Unit): UrlPath {
    this.queryParameters = QueryParameters().apply(parameters)
    return this
}

fun Any.toDslProperty(): DslProperty<Any> {
    return DslProperty(this)
}

fun Map<String, Any>.toDslProperties(): Map<String, DslProperty<Any>> {
    return entries.stream().collect(Collectors.toMap(
            { entry -> entry.key },
            { entry -> entry.value.toDslProperty() },
            { t, _ -> throw IllegalStateException(String.format("Duplicate key %s", t)) },
            { LinkedHashMap<String, DslProperty<Any>>() }
    ))
}

fun List<Any>.toDslProperties(): List<DslProperty<Any>> {
    return stream().map(Any::toDslProperty).collect(Collectors.toList())
}