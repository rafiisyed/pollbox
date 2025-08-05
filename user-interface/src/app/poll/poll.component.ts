import { Component, OnInit } from '@angular/core';
import { PollService } from '../poll.service';
import { Poll } from '../poll.models';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-poll',
  imports: [CommonModule, FormsModule],
  templateUrl: './poll.component.html',
  styleUrl: './poll.component.css'
})
export class PollComponent implements OnInit{

  newPoll: Poll = {
    id: 0,
    question: '',
    options: [
      {
        optionText: '',
        voteCount: 0
      },
      {
        optionText: '',
        voteCount: 0
      }
    ]
  }

  polls: Poll[] = [];

  constructor (private pollService: PollService) {
    
  }

  ngOnInit(): void {
    this.loadPolls();
  }

  loadPolls() {
    this.pollService.getPolls().subscribe({
      next: (polls) => {
        this.polls = polls;
        console.log('Polls loaded:', polls);
      },
      error: (err) => {
        console.error('Error loading polls:', err);
      }
    });
  }


  createPoll() {
    //check if question is empty
    if (!this.newPoll.question.trim()) {
      alert("Poll question cannot be empty!");
      return;
    }

    //check if all options are filled and > 2
    const validOptions = this.newPoll.options.filter(opt => opt.optionText && opt.optionText.trim() !== '');
    if (validOptions.length < 2) {
      alert('Please provide at least 2 non-empty options!');
      return;
    }

    this.newPoll.options = validOptions;

  this.pollService.createPoll(this.newPoll).subscribe({
    next: (createdPoll) => {
      console.log('✅ Poll created:', createdPoll);

      // Add the new poll to the list of polls shown
      this.polls.push(createdPoll);

      // Reset the form for new poll input
      this.resetPoll();
      
    },
    error: (err) => {
      console.error('❌ Error creating poll:', err);
      // Optionally show error to user using toast/snackbar etc.
    }
  });
}
  //adds options to the UI
  addOption() {
    if (this.newPoll.options.length < 10) {
      this.newPoll.options.push({ optionText: '', voteCount: 0 });
    } else {
      console.warn('Maximum of 10 options reached');
    }
  }

  removeOption(index: number): void {
  this.newPoll.options.splice(index, 1);
}


  vote(pollId: number, optionIndex: number) {
    this.pollService.vote(pollId, optionIndex).subscribe({
      next: () => {
        console.log(`✅ Voted for option ${optionIndex + 1} in poll ${pollId}`);
        // Optionally refresh the polls to reflect the new vote count
        this.loadPolls();
      },
      error: (err) => {
        console.error(`❌ Error voting in poll ${pollId}:`, err);
        // Optionally show error to user using toast/snackbar etc.
      }
    });
  }

  resetPoll(): void {
    this.newPoll = {
        id: 0,
        question: '',
        options: [
          { optionText: '', voteCount: 0 },
          { optionText: '', voteCount: 0 }
        ]
      };
  }

  //deletes the poll from the database
  delete(pollId: number): void {
    this.pollService.deletePoll(pollId).subscribe({
      next: () => {
        console.log(`✅ Poll ${pollId} deleted successfully`);
        // Refresh the polls list after deletion
        this.loadPolls();
      },
      error: (err) => {
        console.error(`❌ Error deleting poll ${pollId}:`, err);
      }
    });
  }

 toggleTheme(): void {
  document.body.classList.toggle('dark-theme');
}
}