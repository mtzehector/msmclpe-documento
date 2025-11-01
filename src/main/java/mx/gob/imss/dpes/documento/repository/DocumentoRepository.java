package mx.gob.imss.dpes.documento.repository;

import java.util.List;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentoRepository extends JpaRepository<McltDocumento, Long>,
    JpaSpecificationExecutor<McltDocumento> {
    McltDocumento findByCvePersonaAndTipoDocumento(Long cvePersona,Long tipoDocumento);
    @Query(value = "SELECT * FROM mclt_documento " +
                   "WHERE cve_persona = :cve_persona "
                   + "AND cve_tipo_documento = :cve_tipo_documento "
                   + "AND fec_registro_baja IS NULL",
            nativeQuery = true)
    McltDocumento findByPersonaAndTipo(@Param("cve_persona") Long cvePersona,
            @Param("cve_tipo_documento") Long tipoDocumento);
    List<McltDocumento> findByCveSolicitudAndTipoDocumento(Long cveSolicitud,Long tipoDocumento);
    List<McltDocumento> findByCvePersona(Long cvePersona);
    List<McltDocumento> findByCveEntidadFinanciera(Long cveEntidadFinanciera);

    McltDocumento findByCveSolicitudAndRefDocBovedaNotNull(Long cveSolicitud);

    
}
