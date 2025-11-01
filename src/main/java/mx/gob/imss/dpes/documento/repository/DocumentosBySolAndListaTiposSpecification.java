package mx.gob.imss.dpes.documento.repository;

import java.util.List;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import mx.gob.imss.dpes.documento.entity.McltDocumento_;

public class DocumentosBySolAndListaTiposSpecification extends BaseSpecification<McltDocumento> {

  private final long cveSolicitud;
  private final List<Long> tiposDocumento;

  public DocumentosBySolAndListaTiposSpecification(long cveSolicitud, List<Long> tiposDocumento) {
      this.cveSolicitud = cveSolicitud;
      this.tiposDocumento = tiposDocumento;
  }

  @Override
  public Predicate toPredicate(Root<McltDocumento> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.and( cb.equal(root.get(McltDocumento_.cveSolicitud),
        this.cveSolicitud), 
             root.get(McltDocumento_.TIPO_DOCUMENTO).in(this.tiposDocumento ) );
  }
}
