import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home/home.component';
import { ListComponent } from './components/list/list.component';
import { DetailsComponent } from './components/details/details.component';

const routes: Routes = [
  {
    path: '',
    component: ListComponent,
    // redirectTo: '/add',
    pathMatch: 'full',
  },
  {
    path: 'add',
    component: HomeComponent,
  },
  {
    path: 'details/:studentId',
    component: DetailsComponent,
  }

  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
