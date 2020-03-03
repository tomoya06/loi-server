package com.tomoya06.loiserver.model.repo;

import com.tomoya06.loiserver.model.DO.CharacterEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author pengjiahui
 */
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

  List<CharacterEntity> findTopByCreateTmExists(Pageable pageable);

  @Query(nativeQuery = true, value = "select * "
      + "from character where `word` like '?1%'"
      + "order by replace(`word`, ?1, '')")
  List<CharacterEntity> searchWord(String word, Pageable pageable);

  Boolean existsByWord(String word);
}
