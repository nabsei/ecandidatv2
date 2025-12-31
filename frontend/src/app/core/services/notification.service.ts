import { inject, Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private readonly toastr = inject(ToastrService);

  success(message: string, title?: string): void {
    this.toastr.success(message, title, { timeOut: 5000 });
  }

  error(message: string, title?: string): void {
    this.toastr.error(message, title, { timeOut: 5000 });
  }

  warning(message: string, title?: string): void {
    this.toastr.warning(message, title, { timeOut: 5000 });
  }

  info(message: string, title?: string): void {
    this.toastr.info(message, title, { timeOut: 5000 });
  }
}
