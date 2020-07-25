import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DocumentComponentsPage, DocumentDeleteDialog, DocumentUpdatePage } from './document.page-object';

const expect = chai.expect;

describe('Document e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentComponentsPage: DocumentComponentsPage;
  let documentUpdatePage: DocumentUpdatePage;
  let documentDeleteDialog: DocumentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Documents', async () => {
    await navBarPage.goToEntity('document');
    documentComponentsPage = new DocumentComponentsPage();
    await browser.wait(ec.visibilityOf(documentComponentsPage.title), 5000);
    expect(await documentComponentsPage.getTitle()).to.eq('blogSpringAngularApp.document.home.title');
    await browser.wait(ec.or(ec.visibilityOf(documentComponentsPage.entities), ec.visibilityOf(documentComponentsPage.noResult)), 1000);
  });

  it('should load create Document page', async () => {
    await documentComponentsPage.clickOnCreateButton();
    documentUpdatePage = new DocumentUpdatePage();
    expect(await documentUpdatePage.getPageTitle()).to.eq('blogSpringAngularApp.document.home.createOrEditLabel');
    await documentUpdatePage.cancel();
  });

  it('should create and save Documents', async () => {
    const nbButtonsBeforeCreate = await documentComponentsPage.countDeleteButtons();

    await documentComponentsPage.clickOnCreateButton();

    await promise.all([
      documentUpdatePage.setFileNameInput('fileName'),
      documentUpdatePage.documentTypeSelectLastOption(),
      documentUpdatePage.setDocumentFormatInput('documentFormat'),
      documentUpdatePage.setUploadDirInput('uploadDir'),
      documentUpdatePage.userSelectLastOption(),
      documentUpdatePage.blogSelectLastOption(),
    ]);

    expect(await documentUpdatePage.getFileNameInput()).to.eq('fileName', 'Expected FileName value to be equals to fileName');
    expect(await documentUpdatePage.getDocumentFormatInput()).to.eq(
      'documentFormat',
      'Expected DocumentFormat value to be equals to documentFormat'
    );
    expect(await documentUpdatePage.getUploadDirInput()).to.eq('uploadDir', 'Expected UploadDir value to be equals to uploadDir');

    await documentUpdatePage.save();
    expect(await documentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await documentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Document', async () => {
    const nbButtonsBeforeDelete = await documentComponentsPage.countDeleteButtons();
    await documentComponentsPage.clickOnLastDeleteButton();

    documentDeleteDialog = new DocumentDeleteDialog();
    expect(await documentDeleteDialog.getDialogTitle()).to.eq('blogSpringAngularApp.document.delete.question');
    await documentDeleteDialog.clickOnConfirmButton();

    expect(await documentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
