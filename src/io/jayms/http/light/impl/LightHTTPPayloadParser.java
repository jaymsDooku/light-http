package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPMethod;
import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPPayloadParser;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LightHTTPPayloadParser implements HTTPPayloadParser {

    @Override
    public HTTPPayload parse(SocketAddress address, List<ByteBuffer> buffers) {
        System.out.println("Parsing " + buffers.size() + " buffer(s) into a payload...");
        HeaderParseResult headerParseResult = parseHeader(buffers);

        String header = headerParseResult.header;
        String[] headerLines = header.split("\r\n");
        String protocol = headerLines[0];
        headerLines = Arrays.copyOfRange(headerLines, 1, headerLines.length);
        Map<String, Object> payloadHeader = new HashMap<>();
        for (String headerLine : headerLines) {
            String[] headerLineParts = headerLine.split(":");
            //System.out.println("headerLineParts: " + Arrays.toString(headerLineParts));
            headerLineParts[1] = headerLineParts[1].substring(1);
            payloadHeader.put(headerLineParts[0], headerLineParts[1]);
        }

        List<ByteBuffer> remainingBuffers = headerParseResult.remainingBuffers;

        String[] protocolParts = protocol.split(" ");
        String protocolMethod = protocolParts[0];
        String protocolPath = protocolParts[1];
        String protocolVersion = protocolParts[2];
        HTTPMethod method = HTTPMethod.valueOf(protocolMethod);
        HTTPPayload payload = LightHTTPRequest.builder(address)
                .header(payloadHeader)
                .method(method)
                .path(protocolPath)
                .build();
        System.out.println("payloadHeader: " + payloadHeader);
        return payload;
    }

    private HeaderParseResult parseHeader(List<ByteBuffer> buffers) {
        StringBuilder headerBuilder = new StringBuilder();

        List<ByteBuffer> remainingBuffers = null;
        Iterator<ByteBuffer> buffersIterator = buffers.iterator();
        while (buffersIterator.hasNext()) {
            ByteBuffer byteBuffer = buffersIterator.next();
            buffersIterator.remove();

            CharBuffer charBuffer = StandardCharsets.US_ASCII.decode(byteBuffer);
            String data = new String(charBuffer.array());
            System.out.println("data: " + data);
            String[] dataParts = data.split("\r\n\r\n");
            if (dataParts.length > 0) {
                headerBuilder.append(dataParts[0]);

                remainingBuffers = new ArrayList<>();
                if (dataParts.length > 1) {
                    String remainingData = dataParts[1];
                    ByteBuffer remainingDataBuffer = StandardCharsets.US_ASCII.encode(remainingData);
                    remainingBuffers.add(remainingDataBuffer);
                }
                buffersIterator.forEachRemaining(remainingBuffers::add);
                break;
            } else {
                headerBuilder.append(data);
            }
        }

        HeaderParseResult result = new HeaderParseResult();
        result.header = headerBuilder.toString();
        result.remainingBuffers = remainingBuffers;
        return result;
    }

    private class HeaderParseResult {

        String header;
        List<ByteBuffer> remainingBuffers;

    }

}
