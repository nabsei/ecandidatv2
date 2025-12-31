import { Role } from './role';

export class User {
  constructor(
    public id: string,
    public email: string,
    public firstname: string,
    public lastname: string,
    public authorities: Role[],
    public accountNonExpired: boolean = true,
    public accountNonLocked: boolean = true,
    public credentialsNonExpired: boolean = true,
    public enabled: boolean = true
  ) {}
}
