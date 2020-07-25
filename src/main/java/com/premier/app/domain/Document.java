package com.premier.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.premier.app.domain.enumeration.DocumentType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @NotNull
    @Column(name = "document_format", nullable = false)
    private String documentFormat;

    @NotNull
    @Column(name = "upload_dir", nullable = false)
    private String uploadDir;

    @ManyToOne
    @JsonIgnoreProperties(value = "documents", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "documents", allowSetters = true)
    private Blog blog;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public Document fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public Document documentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public Document documentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
        return this;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public Document uploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
        return this;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public User getUser() {
        return user;
    }

    public Document user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public Document blog(Blog blog) {
        this.blog = blog;
        return this;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentFormat='" + getDocumentFormat() + "'" +
            ", uploadDir='" + getUploadDir() + "'" +
            "}";
    }
}
