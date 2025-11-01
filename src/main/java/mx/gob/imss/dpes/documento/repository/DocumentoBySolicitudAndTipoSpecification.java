package mx.gob.imss.dpes.documento.repository;

import mx.gob.imss.dpes.common.enums.TipoDocumentoEnum;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import mx.gob.imss.dpes.documento.entity.McltDocumento_;

public class DocumentoBySolicitudAndTipoSpecification extends BaseSpecification<McltDocumento> {

  private final long idSolicitud;
  private final TipoDocumentoEnum tipoDocumento;

  public DocumentoBySolicitudAndTipoSpecification(long idSolicitud, TipoDocumentoEnum tipoDocumento) {
    this.idSolicitud = idSolicitud;
    this.tipoDocumento = tipoDocumento;
  }

  @Override
  public Predicate toPredicate(Root<McltDocumento> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.and(
      cb.equal(root.get(McltDocumento_.cveSolicitud),
        this.idSolicitud), 
      cb.equal(root.get(McltDocumento_.tipoDocumento),
        tipoDocumento.toValue()));
  }
}
