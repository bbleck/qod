package edu.cnm.deepdive.qod.model.dao;

import edu.cnm.deepdive.qod.model.entity.Quote;
import edu.cnm.deepdive.qod.model.entity.Source;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, Long>{
  //quote is the object type, long is the key

  List<Quote> findAllByTextContainingOrderByText(String fragment);
  //spring will generate the sql code for the above line

  List<Quote> findAllByOrderByText();

  List<Quote> findAllBySourceContainingOrderBySourceAscTextAsc(String fragment);

//  List<Quote> findAllBySourceContainingAndTextContainingOrderBySourceAscTextAsc(String sourceFrag, String textFrag);

  List<Quote> findAllBySourceOrderByText(Source source);

  Optional<Quote> findFirstBySourceAndId(Source source, long quoteId);


  @Query(value = "SELECT * FROM Quote ORDER BY RANDOM() FETCH FIRST 1 ROW ONLY", nativeQuery = true)
  Optional<Quote> findRandom();



}
