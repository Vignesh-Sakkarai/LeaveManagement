import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';

import { UserComponent } from './user.component';

describe('UserComponent', () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserComponent ],
      imports : [RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain the signIn tab Id as (signinId and signupId)', () => {
    let signinElement: DebugElement;
    let signupElement: DebugElement;
    signinElement = fixture.debugElement.query(By.css('#signinId'));
    signupElement = fixture.debugElement.query(By.css('#signupId'));
    expect(signinElement).toBeTruthy();
    expect(signupElement).toBeTruthy();
  });
});
