import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSpringAngularAppSharedModule } from 'app/shared/shared.module';
import { BlogComponent } from './blog.component';
import { BlogDetailComponent } from './blog-detail.component';
import { BlogUpdateComponent } from './blog-update.component';
import { BlogDeleteDialogComponent } from './blog-delete-dialog.component';
import { blogRoute } from './blog.route';

@NgModule({
  imports: [BlogSpringAngularAppSharedModule, RouterModule.forChild(blogRoute)],
  declarations: [BlogComponent, BlogDetailComponent, BlogUpdateComponent, BlogDeleteDialogComponent],
  entryComponents: [BlogDeleteDialogComponent],
})
export class BlogSpringAngularAppBlogModule {}
