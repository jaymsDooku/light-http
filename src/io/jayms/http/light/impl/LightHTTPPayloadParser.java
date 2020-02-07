package io.jayms.http.light.impl;

import io.jayms.http.light.interfaces.HTTPPayload;
import io.jayms.http.light.interfaces.HTTPPayloadParser;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LightHTTPPayloadParser implements HTTPPayloadParser {

    @Override
    public HTTPPayload parse(List<ByteBuffer> buffers) {
        System.out.println("Parsing " + buffers.size() + " buffer(s) into a payload...");
        StringBuilder header = new StringBuilder();

        List<ByteBuffer> remainingBuffers;
        Iterator<ByteBuffer> buffersIterator = buffers.iterator();
        while (buffersIterator.hasNext()) {
            ByteBuffer byteBuffer = buffersIterator.next();
            buffersIterator.remove();

            CharBuffer charBuffer = StandardCharsets.US_ASCII.decode(byteBuffer);
            String data = new String(charBuffer.array());
            System.out.println("data: " + data);
            String[] dataParts = data.split("\r\n\r\n");
            if (dataParts.length > 0) {
                header.append(dataParts[0]);

                remainingBuffers = new ArrayList<>();
                if (dataParts.length > 1) {
                    String remainingData = dataParts[1];
                    ByteBuffer remainingDataBuffer = StandardCharsets.US_ASCII.encode(remainingData);
                    remainingBuffers.add(remainingDataBuffer);
                }
                buffersIterator.forEachRemaining(remainingBuffers::add);
                break;
            } else {
                header.append(data);
            }
        }

        System.out.println("header: " + header);
        return null;
    }
}
