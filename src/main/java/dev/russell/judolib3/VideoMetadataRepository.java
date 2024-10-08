package dev.russell.judolib3;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "videos", path = "videos")
public interface VideoMetadataRepository extends MongoRepository<VideoMetaData, String> {
    public VideoMetaData findByName(@Param("name") String name);
}