import { IUser } from 'app/core/user/user.model';
import { IBlog } from 'app/shared/model/blog.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';

export interface IDocument {
  id?: number;
  fileName?: string;
  documentType?: DocumentType;
  documentFormat?: string;
  uploadDir?: string;
  user?: IUser;
  blog?: IBlog;
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public fileName?: string,
    public documentType?: DocumentType,
    public documentFormat?: string,
    public uploadDir?: string,
    public user?: IUser,
    public blog?: IBlog
  ) {}
}
