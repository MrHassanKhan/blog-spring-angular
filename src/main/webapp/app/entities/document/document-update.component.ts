import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDocument, Document } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IBlog } from 'app/shared/model/blog.model';
import { BlogService } from 'app/entities/blog/blog.service';

type SelectableEntity = IUser | IBlog;

@Component({
  selector: 'jhi-document-update',
  templateUrl: './document-update.component.html',
})
export class DocumentUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  blogs: IBlog[] = [];

  editForm = this.fb.group({
    id: [],
    fileName: [null, [Validators.required]],
    documentType: [null, [Validators.required]],
    documentFormat: [null, [Validators.required]],
    uploadDir: [null, [Validators.required]],
    user: [],
    blog: [],
  });

  constructor(
    protected documentService: DocumentService,
    protected userService: UserService,
    protected blogService: BlogService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ document }) => {
      this.updateForm(document);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.blogService.query().subscribe((res: HttpResponse<IBlog[]>) => (this.blogs = res.body || []));
    });
  }

  updateForm(document: IDocument): void {
    this.editForm.patchValue({
      id: document.id,
      fileName: document.fileName,
      documentType: document.documentType,
      documentFormat: document.documentFormat,
      uploadDir: document.uploadDir,
      user: document.user,
      blog: document.blog,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const document = this.createFromForm();
    if (document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(document));
    }
  }

  private createFromForm(): IDocument {
    return {
      ...new Document(),
      id: this.editForm.get(['id'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      documentType: this.editForm.get(['documentType'])!.value,
      documentFormat: this.editForm.get(['documentFormat'])!.value,
      uploadDir: this.editForm.get(['uploadDir'])!.value,
      user: this.editForm.get(['user'])!.value,
      blog: this.editForm.get(['blog'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
