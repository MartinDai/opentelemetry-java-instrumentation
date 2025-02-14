/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.grpc.v1_6.internal;

import io.grpc.Status;
import io.opentelemetry.instrumentation.api.instrumenter.net.InetSocketAddressNetClientAttributesGetter;
import io.opentelemetry.instrumentation.grpc.v1_6.GrpcRequest;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import javax.annotation.Nullable;

/**
 * This class is internal and is hence not for public use. Its APIs are unstable and can change at
 * any time.
 */
public final class GrpcNetClientAttributesGetter
    extends InetSocketAddressNetClientAttributesGetter<GrpcRequest, Status> {

  @Override
  public String transport(GrpcRequest request, @Nullable Status response) {
    return SemanticAttributes.NetTransportValues.IP_TCP;
  }

  @Nullable
  @Override
  public String peerName(GrpcRequest grpcRequest, @Nullable Status status) {
    return grpcRequest.getLogicalHost();
  }

  @Override
  public Integer peerPort(GrpcRequest grpcRequest, @Nullable Status status) {
    return grpcRequest.getLogicalPort();
  }

  @Override
  @Nullable
  protected InetSocketAddress getPeerSocketAddress(GrpcRequest request, @Nullable Status response) {
    SocketAddress address = request.getPeerSocketAddress();
    if (address instanceof InetSocketAddress) {
      return (InetSocketAddress) address;
    }
    return null;
  }
}
