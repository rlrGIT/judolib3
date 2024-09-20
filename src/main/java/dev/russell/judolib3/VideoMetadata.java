package dev.russell.judolib3;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
record VideoMetadata(
        @Id
        String name,
        String description,
        String blobKey,
        String thumbnailKey
) {

    @Override
    public String toString() {
        return String.format(
                "Video: [name=%s, blobKey=%s, thumbnailKey=%s]",
                name, blobKey, thumbnailKey
        );
    }
}
