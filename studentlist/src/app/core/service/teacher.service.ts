import { Injectable } from '@angular/core';
import { ApiService } from '../service/apiService';
import { Teacher } from '../models/teacher.model';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {
  private endpoint = 'teacher';

  constructor(private api: ApiService<Teacher>) {}

  getAll() {
    return this.api.getAll(this.endpoint);
  }

  getById(id: number) {
    return this.api.getById(this.endpoint, id);
  }

  searchByName(name: string) {
    return this.api.searchByName(this.endpoint, 'fullName', name);
  }

  create(teacher: Teacher) {
    return this.api.create(this.endpoint, teacher);
  }

  update(id: number, teacher: Teacher) {
    return this.api.update(this.endpoint, id, teacher);
  }

  delete(id: number) {
    return this.api.delete(this.endpoint, id);
  }

  importTeachers(file: File) {
    return this.api.importFile(this.endpoint, file);
  }

  exportTeachers() {
    return this.api.export(this.endpoint);
  }
}
