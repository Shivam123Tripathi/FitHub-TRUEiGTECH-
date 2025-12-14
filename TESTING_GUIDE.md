# Quick Testing Guide - Trainer Search & Follow Feature

## Prerequisites

- MySQL server running
- Backend running on port 8080
- Frontend running on port 3000
- At least one trainer account created (via signup)
- At least one user account created (via signup)

## Step-by-Step Testing

### 1. Start the Application

```bash
# Terminal 1 - Backend
cd backend
mvn spring-boot:run

# Terminal 2 - Frontend
cd frontend
npm start
```

### 2. Create Test Accounts (if not already created)

**Create Trainer Account**:

1. Go to http://localhost:3000
2. Click "Get Started"
3. Select "I'm a Trainer"
4. Fill in signup form:
   - Full Name: John Doe
   - Email: trainer@test.com
   - Password: Test123!
   - Years of Experience: 5
   - Specializations: Cardio, Weight Loss, HIIT
   - Bio: Experienced fitness trainer
5. Click "Sign Up"

**Create User Account**:

1. Go to http://localhost:3000
2. Click "Get Started"
3. Select "I'm a User"
4. Fill in signup form with your details
5. Click "Sign Up"

### 3. Test Trainer Search Feature

**As a User**:

1. Login with user credentials
2. You should see the User Dashboard with 3 tabs:

   - ✅ "All Plans"
   - ✅ "Following"
   - ✅ "Find Trainers" (NEW!)

3. Click on **"Find Trainers"** tab

4. Verify you can see:
   - ✅ Search input box with placeholder text
   - ✅ Orange "Search" button
   - ✅ List of trainer cards showing:
     - Trainer name (in orange)
     - Specializations
     - Years of experience
     - Bio (if available)
     - "Follow" button (orange)

### 4. Test Search Functionality

**Search by Name**:

1. Type trainer's name in search box
2. Press Enter or click "Search" button
3. ✅ Should show only matching trainers

**Search by Specialization**:

1. Type "cardio" in search box
2. Press Enter or click "Search" button
3. ✅ Should show trainers with "cardio" in specializations

**Clear Search**:

1. Clear search box
2. Click "Search" button
3. ✅ Should show all trainers again

### 5. Test Follow Functionality

**Follow a Trainer**:

1. Click "Follow" button on any trainer card
2. ✅ Should see success alert
3. ✅ Button should change to "Unfollow" (outlined style)
4. ✅ Button text should change

**Unfollow a Trainer**:

1. Click "Unfollow" button on a followed trainer
2. ✅ Should see success alert
3. ✅ Button should change back to "Follow" (solid orange)

### 6. Test Follow Persistence

**Verify Follow Status Persists**:

1. Follow a trainer
2. Refresh the page (F5)
3. Navigate back to "Find Trainers" tab
4. ✅ Previously followed trainer should still show "Unfollow" button

### 7. Test Integration with Following Tab

**View Plans from Followed Trainers**:

1. Follow at least one trainer who has created fitness plans
2. Click on "Following" tab
3. ✅ Should see plans from only the trainers you follow
4. ✅ If no plans exist, should show: "You're not following any trainers yet..."

### 8. Test UI/UX

**Dark Theme Consistency**:

- ✅ All new components use dark background (#2A2A2A)
- ✅ Orange accents (#FF6A00) on buttons and borders
- ✅ Hover effects work smoothly
- ✅ Text is readable (white on dark)

**Responsive Design**:

- ✅ Trainer cards arrange in grid
- ✅ Cards scale on hover
- ✅ Search input is full-width on mobile

**Loading States**:

- ✅ Shows "Loading trainers..." while fetching
- ✅ No flickering or broken layouts

### 9. Test Error Handling

**Empty Search Results**:

1. Search for "xyz123nonexistent"
2. ✅ Should show: "No trainers found. Try a different search term."

**Network Error** (optional):

1. Stop backend server
2. Try to follow a trainer
3. ✅ Should show: "Unable to connect to server"

### 10. Test Security

**JWT Authentication**:

1. Clear localStorage (Browser DevTools → Application → Local Storage)
2. Try to access http://localhost:3000/user/feed
3. ✅ Should redirect to login page

**Duplicate Follow Prevention**:

1. Follow a trainer
2. Check browser console
3. Try to follow the same trainer again by refreshing and clicking "Follow"
4. ✅ Should show: "You are already following this trainer"

## Expected Results Summary

✅ All trainers from database are displayed
✅ Search works for both name and specialization (case-insensitive)
✅ Follow/unfollow operations work instantly
✅ Follow status persists after page refresh
✅ UI matches dark theme with orange accents
✅ No errors in browser console
✅ JWT authentication is enforced
✅ Duplicate follows are prevented
✅ Existing functionality (plans, subscriptions) still works

## Troubleshooting

**Trainers not showing?**

- Verify at least one trainer account exists in database
- Check backend logs for errors
- Verify JWT token is valid (check localStorage)

**Follow button not working?**

- Check browser console for errors
- Verify backend is running
- Check that user is logged in (not trainer)

**Search not working?**

- Check that keyword is being sent correctly
- Verify backend endpoint is accessible
- Check MySQL database has trainers with searchable data

## Database Verification (Optional)

Connect to MySQL and run:

```sql
-- Check trainers exist
SELECT id, full_name, specializations, years_of_experience FROM trainers;

-- Check follows table
SELECT f.id, u.full_name as user_name, t.full_name as trainer_name, f.followed_at
FROM follows f
JOIN users u ON f.user_id = u.id
JOIN trainers t ON f.trainer_id = t.id;
```

## Success Criteria

- ✅ Feature is fully functional
- ✅ No breaking changes to existing features
- ✅ Dark theme maintained
- ✅ JWT security working
- ✅ User experience is smooth
