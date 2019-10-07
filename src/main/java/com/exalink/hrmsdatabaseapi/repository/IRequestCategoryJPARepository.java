package com.exalink.hrmsdatabaseapi.repository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.formstructures.RequestCategory;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IRequestCategoryJPARepository extends JpaRepository<RequestCategory, Long>{

	@Query("SELECT new map(r.id as id, r.submissionService as submissionService, r.queryService as queryService) FROM RequestCategory r where r.requestCategory = :name AND r.type = :type")
	Map<String, Object> loadRequestCategory(@Param("name") String name, @Param("type") String type);
	
	@Query("SELECT new map(l.id as id, l.name as name, l.type as type, l.httpRequest as httpRequest, l.shortDescription as shortDescription, l.longDescription as longDescription, l.note as note) FROM Layout l where l.requestCategoryId.id = :requestCategory AND l.type = :type")
	Map<String, Object> loadLayoutByRequestCategory(@Param("requestCategory") UUID requestCategory, @Param("type") String type);
	
	@Query("SELECT new map(e.id as id, e.name as name, e.label as label, e.showLabel as showLabel, e.controlType as controlType, e.uiSequence as uiSequence, e.required as required, e.defaultValue as defaultValue, e.apiQuery as apiQuery, e.visibility as visibility, e.specialNote as specialNote, e.dependentOn as dependentOn, e.isDependent as isDependent) FROM Element e where e.layoutId.id = :layout")
	Set<Map<String, Object>> loadElementsByLayout(@Param("layout") UUID layout);
	
	@Query("SELECT new map(e.id as id, e.name as name, e.label as label, e.showLabel as showLabel, e.controlType as controlType, e.uiSequence as uiSequence, e.required as required, e.defaultValue as defaultValue, e.apiQuery as apiQuery, e.visibility as visibility, e.specialNote as specialNote, e.dependentOn as dependentOn, e.isDependent as isDependent) FROM Element e where e.fieldSetId.id = :fieldSetId")
	Set<Map<String, Object>> loadElementsByFieldSet(@Param("fieldSetId") UUID fieldSetId);
	
	@Query("SELECT new map(e.id as id, e.name as name, e.cssClass as cssClass, e.type as type) FROM FieldSets e where e.layoutId.id = :layout AND e.type = :type")
	Set<Map<String, Object>> loadFieldSetsByLayout(@Param("layout") UUID layout, @Param("type") String type);
}
