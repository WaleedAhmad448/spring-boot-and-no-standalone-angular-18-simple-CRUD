
import{ Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { StudentService } from '../../core/service/student.service';


 
@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
    })
    export class NavbarComponent {
     

        constructor(
            private router: Router,
         
        ) { }
        navigateToHome() {
            this.router.navigate(['/home']);
        }
    }