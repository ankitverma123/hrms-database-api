package com.exalink.hrmsdatabaseapi.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ICandidateSourceRepository extends JpaRepository<CandidateSources, Long>, JpaSpecificationExecutor<CandidateSources>{
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateSources c WHERE c.id = :id")
    boolean candidateSourceExistance(@Param("id") Long id);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateSources c WHERE c.name = :name")
    boolean candidateSourceExistance(@Param("name") String name);
	
    Optional<CandidateSources> findById(Long id);
    
    Optional<CandidateSources> findByName(String name);
    
    @Query("FROM CandidateSources c WHERE c.isActive is true")
    List<CandidateSources> listActiveCandidateSources();
    
    @Query(value= "SELECT s.name as label, CAST(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS DECIMAL(18, 0)) as value "
    		+ "FROM candidate c "
    		+ "JOIN candidate_sources s "
    		+ "ON c.source=s.id "
    		+ "WHERE s.is_active IS TRUE "
    		+ "GROUP BY label", nativeQuery=true)
    List<Map<String, Object>> candidateSourcesInPercentage();
    
    @Query(value= "SELECT s.status as label, CAST(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS DECIMAL(18, 0)) as value "
    		+ "FROM candidate c "
    		+ "JOIN onboard_status s "
    		+ "ON c.onboard_status=s.id "
    		+ "GROUP BY label", nativeQuery=true)
    List<Map<String, Object>> recruitmentStatusInPercentage();
    
    @Query(value= "SELECT os.status as label, COUNT(*) AS value "
    		+ "FROM candidate c "
    		+ "JOIN onboard_status os "
    		+ "ON c.onboard_status=os.id "
    		+ "GROUP BY label", nativeQuery=true)
    List<Map<String, Object>> recruitmentStatusInNumbers();
    
    @Query(value= "SELECT c.gender as label, COUNT(*) AS value FROM candidate c GROUP BY label", nativeQuery=true)
    List<Map<String, Object>> genderMixVisualisation();
    
    @Query(value= "SELECT c.gender as label,  CAST(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS DECIMAL(18, 0)) as value FROM candidate c GROUP BY label", nativeQuery=true)
    List<Map<String, Object>> genderMixVisualisationInPercentage();
    
    
    @Query(value= "SELECT cdc.category as label, COUNT(*) AS value FROM candidate c "
    		+ "JOIN candidate_offer co ON c.candidate_offer_status=co.id "
    		+ "JOIN offer_status os ON co.status=os.id "
    		+ "JOIN offer_decline_category cdc ON co.decline_category=cdc.id "
    		+ "GROUP BY label", nativeQuery=true)
    List<Map<String, Object>> offerDeclineVisualisation();
    
    @Query(value= "SELECT cdc.category as label, CAST(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS DECIMAL(18, 0)) as value FROM candidate c "
    		+ "JOIN candidate_offer co ON c.candidate_offer_status=co.id "
    		+ "JOIN offer_status os ON co.status=os.id "
    		+ "JOIN offer_decline_category cdc ON co.decline_category=cdc.id "
    		+ "GROUP BY label", nativeQuery=true)
    List<Map<String, Object>> offerDeclineVisualisationInPercentage();
}
