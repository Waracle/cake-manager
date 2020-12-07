import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayCakesComponent } from './display-cakes.component';

describe('DisplayCakesComponent', () => {
  let component: DisplayCakesComponent;
  let fixture: ComponentFixture<DisplayCakesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisplayCakesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayCakesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
