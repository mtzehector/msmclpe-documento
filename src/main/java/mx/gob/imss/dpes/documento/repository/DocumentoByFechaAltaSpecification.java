/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.documento.repository;

import java.util.Date;
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
public class DocumentoByFechaAltaSpecification extends BaseSpecification<McltDocumento>{

    private final Date fechaInicio;
    private final Date fechaFin;
    
    public DocumentoByFechaAltaSpecification (Date fechaInicio, Date fechaFin ){
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    @Override
    public Predicate toPredicate(Root<McltDocumento> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.and(cb.greaterThanOrEqualTo(root.<Date>get("altaRegistro"), fechaInicio), cb.lessThanOrEqualTo(root.<Date>get("altaRegistro"), fechaFin));
    }
    
}
