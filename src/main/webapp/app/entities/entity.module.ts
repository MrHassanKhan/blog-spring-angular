import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'blog',
        loadChildren: () => import('./blog/blog.module').then(m => m.BlogSpringAngularAppBlogModule),
      },
      {
        path: 'entry',
        loadChildren: () => import('./entry/entry.module').then(m => m.BlogSpringAngularAppEntryModule),
      },
      {
        path: 'tag',
        loadChildren: () => import('./tag/tag.module').then(m => m.BlogSpringAngularAppTagModule),
      },
      {
        path: 'document',
        loadChildren: () => import('./document/document.module').then(m => m.BlogSpringAngularAppDocumentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BlogSpringAngularAppEntityModule {}
