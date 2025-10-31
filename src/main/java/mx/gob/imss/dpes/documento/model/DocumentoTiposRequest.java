package mx.gob.imss.dpes.documento.model;

import lombok.Data;

import java.util.List;
import mx.gob.imss.dpes.common.model.BaseModel;

@Data
public class DocumentoTiposRequest extends BaseModel {
  private Long cveSolicitud;
  private List<Long> tiposDocumento;

}
