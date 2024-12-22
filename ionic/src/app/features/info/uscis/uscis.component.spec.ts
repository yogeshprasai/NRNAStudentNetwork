/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { UscisComponent } from './uscis.component';

describe('UscisComponent', () => {
  let component: UscisComponent;
  let fixture: ComponentFixture<UscisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UscisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UscisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
