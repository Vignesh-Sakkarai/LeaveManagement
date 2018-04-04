import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core'
import { FormsModule } from '@angular/forms';

import { HeaderComponent } from './header.component';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderComponent ],
      imports: [FormsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have the id leaveMgtHeader', ()=>{
     let element: DebugElement;
     element = fixture.debugElement.query(By.css('#leaveMgtHeader'));
     expect(element).toBeTruthy;
  })

  it('should have the text as Leave Management Application', ()=>{
     let element: DebugElement;
     element = fixture.debugElement.query(By.css('#leaveMgtHeader'));
     expect(element.nativeElement.textContent.trim()).toBe('Leave Management Application');
  })
});
