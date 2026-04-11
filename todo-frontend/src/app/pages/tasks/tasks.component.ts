import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../core/services/task.service';
import { AuthService } from '../../core/services/auth.service';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './tasks.component.html'
})
export class TasksComponent implements OnInit {

  tasks: Task[] = [];
  newTask: Task = { title: '', description: '', completed: false };
  errorMessage = '';

  constructor(
    private taskService: TaskService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.listAll().subscribe({
      next: tasks => {
        this.tasks = tasks,
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorMessage = 'Erro ao carregar tarefas',
        this.cdr.detectChanges();
      }
    });
  }

  createTask(): void {
    if (!this.newTask.title.trim()) return;
    this.taskService.create(this.newTask).subscribe({
      next: () => {
        this.newTask = { title: '', description: '', completed: false };
        this.loadTasks();
      },
      error: () => this.errorMessage = 'Erro ao criar tarefa'
    });
  }

  toggleComplete(task: Task): void {
    task.completed = !task.completed;
    this.taskService.update(task.id!, task).subscribe({
      error: () => this.errorMessage = 'Erro ao atualizar tarefa'
    });
  }

  deleteTask(id: number): void {
    this.taskService.delete(id).subscribe({
      next: () => this.loadTasks(),
      error: () => this.errorMessage = 'Erro ao deletar tarefa'
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}