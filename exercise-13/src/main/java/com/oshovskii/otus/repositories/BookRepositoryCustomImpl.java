//package com.oshovskii.otus.repositories;
//
//import com.oshovskii.otus.models.Author;
//import com.oshovskii.otus.models.Book;
//import com.oshovskii.otus.repositories.interfaces.BookRepositoryCustom;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import lombok.val;
//import org.bson.types.ObjectId;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.aggregation.Aggregation;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//
//import java.util.List;
//
//import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
//import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;
//import static org.springframework.data.mongodb.core.query.Criteria.where;
//
//@RequiredArgsConstructor
//public class BookRepositoryCustomImpl implements BookRepositoryCustom {
//
//    @Data
//    private class ArraySizeProjection {
//        private int size;
//    }
//
//    private final MongoTemplate mongoTemplate;
//
//    @Override
//    public List<Author> getBookAuthorsById(String bookId) {
//        Aggregation aggregation = newAggregation(
//                match(Criteria.where("id").is(bookId))
//                , unwind("authors")
//                , project().andExclude("_id").and(valueOfToArray("authors")).as("authors_map")
//                , project().and("authors_map").arrayElementAt(1).as("authors_id_map")
//                , project().and("authors_id_map.v").as("authors_id")
//                , lookup("Author", "author_id", "_id", "author")
//                , project().and("author._id").as("_id").and("author.name").as("name")
//        );
//        return mongoTemplate.aggregate(aggregation, Book.class, Author.class).getMappedResults();
//    }
//
//    @Override
//    public long getAuthorsArrayLengthById(String id) {
//        val aggregation = Aggregation.newAggregation(
//                match(where("id").is(id)),
//                project().andExclude("_id").and("authors").size().as("size"));
//
//        val arraySizeProjection = mongoTemplate.aggregate(
//                aggregation, Book.class, ArraySizeProjection.class).getUniqueMappedResult();
//        return arraySizeProjection == null ? 0 : arraySizeProjection.getSize();
//    }
//
//    @Override
//    public void removeAuthorsArrayElementsById(String authorId) {
//        val query = Query.query(Criteria.where("$id").is(new ObjectId(authorId)));
//        val update = new Update().pull("authors", query);
//        mongoTemplate.updateMulti(new Query(), update, Book.class);
//    }
//}
