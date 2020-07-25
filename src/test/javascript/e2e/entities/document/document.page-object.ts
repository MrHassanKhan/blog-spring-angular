import { element, by, ElementFinder } from 'protractor';

export class DocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-document div table .btn-danger'));
  title = element.all(by.css('jhi-document div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DocumentUpdatePage {
  pageTitle = element(by.id('jhi-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fileNameInput = element(by.id('field_fileName'));
  documentTypeSelect = element(by.id('field_documentType'));
  documentFormatInput = element(by.id('field_documentFormat'));
  uploadDirInput = element(by.id('field_uploadDir'));

  userSelect = element(by.id('field_user'));
  blogSelect = element(by.id('field_blog'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFileNameInput(fileName: string): Promise<void> {
    await this.fileNameInput.sendKeys(fileName);
  }

  async getFileNameInput(): Promise<string> {
    return await this.fileNameInput.getAttribute('value');
  }

  async setDocumentTypeSelect(documentType: string): Promise<void> {
    await this.documentTypeSelect.sendKeys(documentType);
  }

  async getDocumentTypeSelect(): Promise<string> {
    return await this.documentTypeSelect.element(by.css('option:checked')).getText();
  }

  async documentTypeSelectLastOption(): Promise<void> {
    await this.documentTypeSelect.all(by.tagName('option')).last().click();
  }

  async setDocumentFormatInput(documentFormat: string): Promise<void> {
    await this.documentFormatInput.sendKeys(documentFormat);
  }

  async getDocumentFormatInput(): Promise<string> {
    return await this.documentFormatInput.getAttribute('value');
  }

  async setUploadDirInput(uploadDir: string): Promise<void> {
    await this.uploadDirInput.sendKeys(uploadDir);
  }

  async getUploadDirInput(): Promise<string> {
    return await this.uploadDirInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async blogSelectLastOption(): Promise<void> {
    await this.blogSelect.all(by.tagName('option')).last().click();
  }

  async blogSelectOption(option: string): Promise<void> {
    await this.blogSelect.sendKeys(option);
  }

  getBlogSelect(): ElementFinder {
    return this.blogSelect;
  }

  async getBlogSelectedOption(): Promise<string> {
    return await this.blogSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DocumentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-document-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-document'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
