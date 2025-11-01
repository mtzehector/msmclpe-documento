/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.documento.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import mx.gob.imss.dpes.documento.entity.McltDocumento_;

/**
 *
 * @author juanf.barragan
 */
public class DocumentoByEntidadSpecification extends BaseSpecification<McltDocumento> {
    
    private final String eFinanciera;
    
    public DocumentoByEntidadSpecification(String eFinanciera){
        this.eFinanciera = eFinanciera;
    }

    @Override
    public Predicate toPredicate(Root<McltDocumento> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.or(cb.equal(root.get(McltDocumento_.eFinanciera), this.eFinanciera),cb.isNull(root.get(McltDocumento_.eFinanciera)));
    }
    
}
