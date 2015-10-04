package com.twitter.zipkin.storage

import com.twitter.util.Time
import com.twitter.zipkin.util.Util.checkArgument

/**
 * @param serviceName Mandatory [[com.twitter.zipkin.common.Endpoint.serviceName]]
 * @param spanName When present, only include traces with this [[com.twitter.zipkin.common.Span.name]]
 * @param annotations Include traces whose [[com.twitter.zipkin.common.Span.annotations]] include a value in this set.
 *                    This is an AND condition against the set, as well against [[binaryAnnotations]]
 * @param binaryAnnotations Include traces whose [[com.twitter.zipkin.common.Span.binaryAnnotations]] include a
 *                          String whose key and value are an entry in this set.
 *                          This is an AND condition against the set, as well against [[annotations]]
 * @param endTs only return traces where all [[com.twitter.zipkin.common.Span.endTs]] are at
 *              or before this time in epoch microseconds. Defaults to current time.
 * @param limit maximum number of traces to return. Defaults to 10
 */
case class QueryRequest(serviceName: String,
                        spanName: Option[String] = None,
                        annotations: Set[String] = Set.empty,
                        binaryAnnotations: Set[(String, String)] = Set.empty,
                        endTs: Long = Time.now.inMicroseconds,
                        limit: Int = 10) {

  checkArgument(serviceName.nonEmpty, "serviceName was empty")
  checkArgument(spanName.map(_.nonEmpty).getOrElse(true), "spanName was empty")
  checkArgument(endTs > 0, () => "endTs should be positive, in epoch microseconds: was %d".format(endTs))
  checkArgument(limit > 0, () => "limit should be positive: was %d".format(limit))
}
