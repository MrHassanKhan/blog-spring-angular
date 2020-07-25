package com.premier.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.premier.app.BlogSpringAngularApp;
import com.premier.app.domain.Document;
import com.premier.app.domain.enumeration.DocumentType;
import com.premier.app.repository.DocumentRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@SpringBootTest(classes = BlogSpringAngularApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocumentResourceIT {
    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final DocumentType DEFAULT_DOCUMENT_TYPE = DocumentType.PROFILE;
    private static final DocumentType UPDATED_DOCUMENT_TYPE = DocumentType.BLOG;

    private static final String DEFAULT_DOCUMENT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_UPLOAD_DIR = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_DIR = "BBBBBBBBBB";

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .fileName(DEFAULT_FILE_NAME)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .documentFormat(DEFAULT_DOCUMENT_FORMAT)
            .uploadDir(DEFAULT_UPLOAD_DIR);
        return document;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Document document = new Document()
            .fileName(UPDATED_FILE_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .uploadDir(UPDATED_UPLOAD_DIR);
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();
        // Create the Document
        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testDocument.getDocumentFormat()).isEqualTo(DEFAULT_DOCUMENT_FORMAT);
        assertThat(testDocument.getUploadDir()).isEqualTo(DEFAULT_UPLOAD_DIR);
    }

    @Test
    @Transactional
    public void createDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document with an existing ID
        document.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setFileName(null);

        // Create the Document, which fails.

        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setDocumentType(null);

        // Create the Document, which fails.

        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentFormatIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setDocumentFormat(null);

        // Create the Document, which fails.

        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUploadDirIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setUploadDir(null);

        // Create the Document, which fails.

        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc
            .perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentFormat").value(hasItem(DEFAULT_DOCUMENT_FORMAT)))
            .andExpect(jsonPath("$.[*].uploadDir").value(hasItem(DEFAULT_UPLOAD_DIR)));
    }

    @Test
    @Transactional
    public void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get("/api/documents/{id}", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.documentFormat").value(DEFAULT_DOCUMENT_FORMAT))
            .andExpect(jsonPath("$.uploadDir").value(DEFAULT_UPLOAD_DIR));
    }

    @Test
    @Transactional
    public void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .fileName(UPDATED_FILE_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .uploadDir(UPDATED_UPLOAD_DIR);

        restDocumentMockMvc
            .perform(
                put("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocument.getDocumentFormat()).isEqualTo(UPDATED_DOCUMENT_FORMAT);
        assertThat(testDocument.getUploadDir()).isEqualTo(UPDATED_UPLOAD_DIR);
    }

    @Test
    @Transactional
    public void updateNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(put("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc
            .perform(delete("/api/documents/{id}", document.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
